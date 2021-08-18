const Eureka = require("eureka-js-client").Eureka;
const eurekaHost = "127.0.0.1";
const eurekaPort = 8761;
const hostName = "localhost";
const ipAddr = "192.168.0.101";

exports.registerWithEureka = function () {
  const client = new Eureka({
    instance: {
      app: "keycloakadminserver",
      hostName: hostName,
      instanceId: "keycloakadminserver",
      ipAddr: ipAddr,
      port: {
        $: 8999,
        "@enabled": true,
      },
      vipAddress: "keycloakadminserver",
      dataCenterInfo: {
        "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
        name: "MyOwn",
      },
      registerWithEureka: true,
      fetchRegistry: true,
    },
    eureka: {
      host: eurekaHost,
      port: eurekaPort,
      servicePath: "/eureka/apps/",
      maxRetries: 10,
      requestRetryDelay: 2000,
    },
  });

  client.logger.level("debug");

  client.start((error) => {
    console.log(error || "user service registered");
  });

  function exitHandler(options, exitCode) {
    if (options.cleanup) {
    }
    if (exitCode || exitCode === 0) console.log(exitCode);
    if (options.exit) {
      client.stop();
    }
  }

  client.on("deregistered", () => {
    process.exit();
    console.log("after deregistered");
  });

  client.on("started", () => {
    console.log("eureka host  " + eurekaHost);
  });

  process.on("SIGINT", exitHandler.bind(null, { exit: true }));
};
