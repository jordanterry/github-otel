# Github to New Relic Otel - Design Document

## Introduction

Observing the state of products we produce is crucial for software engineers; allowing us to 
pro-actively identify issues or reactively respond to issue. For developers writing pipelines e.
g. software that builds our applications this is harder to achieve. Afterall, we are mostly 
writing yaml! 

The objective of this project is to provide methods of instrumenting pipelines using the Open 
Telemetry standard. Developers who use this tooling will be able to: 

1. Allow developers to collect data to enable service level support
1. Allow developers to collect data about time of CI execution
1. Collect telemetry in your businesses telemetry tool of choice

## What problems are being solved?

### Enable telemetry CI

Teams should be able to collect pipeline runs into their businesses telemetry tool of choice. We 
want to enable teams of developers ot use common concepts such as SLO and SLIs. They should be 
able to present data in tooling recognised across the businesses the teams work at. 

#### Use Open Telemetry

Pipeline execution is generally well structured to make use of OpenTelemetry. Taking Github Actions 
for example. A workflow run consists of 0..n jobs which themselves consists of 0..n steps being 
executed. This translates nicely into Open Telemetries nested Span architecture. A tantalising 
thought is that we could consider telemetry across the boundaries of the CI to the build tools 
being used under the hood. Imagine being able to step across boundaries from Github Actions into 
a Gradle build. Or a Bitrise build into a Maven build. This gives teams the opportunity to 
identify and investigate issues in appropriate tools.

## Alternatives