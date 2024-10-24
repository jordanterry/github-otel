import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import okhttp3.Interceptor
import uk.co.jordanterry.otel.github.api.AcceptGithubJsonInterceptor
import uk.co.jordanterry.otel.github.api.GithubApiVersionInterceptor
import uk.co.jordanterry.otel.github.api.GithubAuthorizationInterceptor

@Component
public abstract class GithubInterceptorComponent {

    public abstract val interceptors: Set<Interceptor>

    @IntoSet
    @Provides
    internal fun provideGithubApiVersionInterceptor(): Interceptor =
        GithubApiVersionInterceptor()

    @IntoSet
    @Provides
    internal fun provideAcceptGithubJsonInterceptor(): Interceptor =
        AcceptGithubJsonInterceptor()

    @IntoSet
    @Provides
    internal fun provideGithubAuthorizationInterceptor(): Interceptor =
        GithubAuthorizationInterceptor()
}