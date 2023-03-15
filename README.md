# MapBox API Mock

Quarkus based service to mock the [MapBox Directions API v5](https://www.mapbox.com/) used by the [First Responder Demo](https://github.com/wildfly-extras/first-responder-demo).

The service mocks the MapBox REST endpoint `/directions/v5/` using the profile `mapbox/driving`. It returns one of 10 payload samples:

| Payload                                           | Size |
|---------------------------------------------------|-----:|
| [payload0.json](src/main/resources/payload0.json) | 128K |
| [payload1.json](src/main/resources/payload1.json) | 112K |
| [payload2.json](src/main/resources/payload2.json) | 128K |
| [payload3.json](src/main/resources/payload3.json) | 136K |
| [payload4.json](src/main/resources/payload4.json) | 140K |
| [payload5.json](src/main/resources/payload5.json) | 324K |
| [payload6.json](src/main/resources/payload6.json) | 1.1M |
| [payload7.json](src/main/resources/payload7.json) | 604K |
| [payload8.json](src/main/resources/payload8.json) |  28K |
| [payload9.json](src/main/resources/payload9.json) | 460K |

To use this service in the first responder demo backend, use my fork at https://github.com/hpehl/first-responder-demo and specify the `MAPBOX_BASE_URL` system property, when you start EAP. The value specifies the base URL of the MapBox API mock, e.g. http://localhost:9123. 

The service is also available as container at [Quay.io](https://quay.io/repository/hpehl/frdemo-mapbox): `quay.io/hpehl/frdemo-mapbox`
