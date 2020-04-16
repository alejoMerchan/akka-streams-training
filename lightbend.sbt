resolvers in ThisBuild += "lightbend-commercial-mvn" at
  "https://repo.lightbend.com/pass/oAfVB6Vc6KoYyUwmcvg-ntWwmmVRN3UxglhYEkw9SrCQZxMi/commercial-releases"
resolvers in ThisBuild += Resolver.url("lightbend-commercial-ivy",
  url("https://repo.lightbend.com/pass/oAfVB6Vc6KoYyUwmcvg-ntWwmmVRN3UxglhYEkw9SrCQZxMi/commercial-releases"))(Resolver.ivyStylePatterns)