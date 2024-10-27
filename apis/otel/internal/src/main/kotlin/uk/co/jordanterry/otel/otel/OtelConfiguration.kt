package uk.co.jordanterry.otel.otel

import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.exporter.logging.LoggingSpanExporter
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor
import java.util.concurrent.TimeUnit

/**
 * All SDK management takes place here, away from the instrumentation code, which should only access
 * the OpenTelemetry APIs.
 */
public object OtelConfiguration {
    /**
     * Initialize an OpenTelemetry SDK with a [OtlpGrpcSpanExporter] and a [ ].
     *
     * @param jaegerEndpoint The endpoint of your Jaeger instance.
     * @return A ready-to-use [OpenTelemetry] instance.
     */
    public fun initOpenTelemetry(jaegerEndpoint: String): OpenTelemetrySdk {
        val jaegerOtlpExporter =
            OtlpGrpcSpanExporter.builder()
                .setEndpoint(jaegerEndpoint)
                .setTimeout(30, TimeUnit.SECONDS)
                .build()
        val serviceNameResource: Resource =
            Resource.create(Attributes.of(AttributeKey.stringKey("service.name"), "otel-github-workflow"))

        val sdkTracerProvider =
            SdkTracerProvider.builder()
                .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter()))
                .addSpanProcessor(BatchSpanProcessor.builder(jaegerOtlpExporter).build())
                .setResource(Resource.getDefault().merge(serviceNameResource))
                .build()

        val sdk =
            OpenTelemetrySdk.builder()
                .setTracerProvider(sdkTracerProvider)
                .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                .build()

        Runtime.getRuntime().addShutdownHook(Thread { sdkTracerProvider.close() })
        return sdk
    }
}