const express = require("express");
const eurekaHelper = require("./eurekaConfig");
const KcAdminClient = require("keycloak-admin").default;

let studentRole = require("./studentRole.json");
let parentRole = require("./parentRole.json");
let teacherRole = require("./teacherRole.json");

const app = express();
app.use(express.json());
const port = 8999;

const adminClient = new KcAdminClient({
  baseUrl: "http://localhost:8555/auth",
  realmName: "Adminication",
});

let authenticate = async function () {
  await adminClient.auth({
    username: "admin",
    password: "admin",
    grantType: "password",
    clientId: "keycloakAdmin",
    clientSecret: "870b6149-913f-49b6-a81a-6f4051dc515e",
  });
  return true;
};

let addUser = async function (user) {
  const authenticated = await authenticate();
  if (authenticated) {
    const newUser = await adminClient.users.create({
      username: user.username,
      email: user.email,
      emailVerified: true,
      enabled: true,
      credentials: [
        { type: "password", value: user.password, temporary: false },
      ],
      firstName: user.name,
      lastName: user.lastName,
    });

    let currentRole = {};

    if (user.role === "ROLE_STUDENT") {
      currentRole = studentRole;
    } else if (user.role === "ROLE_PARENT") {
      currentRole = parentRole;
    } else if (user.role === "ROLE_TEACHER") {
      currentRole = teacherRole;
    }

    const result = await adminClient.users.addRealmRoleMappings({
      id: newUser.id,
      roles: [
        {
          id: currentRole.id,
          name: currentRole.name,
        },
      ],
    });

    return result;
  }
};

let updateUser = async function (user) {
  const authenticated = await authenticate().catch((error) =>
    console.log(error)
  );
  if (authenticated) {
    await adminClient.users
      .find({
        username: user.originalUsername,
      })
      .then((userInKeycloak) => {
        const userId = userInKeycloak[0].id;
        let userForKeycloak = {
          firstName: user.name,
          lastName: user.lastName,
          email: user.email,
          username: user.username,
        };
        adminClient.users
          .update({ id: userId }, userForKeycloak)
          .catch((error) => console.log(error));
        if (user.changedPassword) {
          adminClient.users
            .resetPassword({
              id: userId,
              credential: {
                temporary: false,
                type: "password",
                value: user.password,
              },
            })
            .then((result) => {
              return result;
            })
            .catch((error) => console.log(error));
        }
      })
      .catch((error) => console.log(error));
  }
};

app.get("/hi", (req, res) => {
  res.send("Hello");
});

app.post("/add", async (req, res) => {
  const result = await addUser(req.body);
  res.send(result);
});

app.put("/update", async (req, res) => {
  const result = await updateUser(req.body);
  res.send(result);
});

// ------------------ Server Config --------------------------------------------
var server = app.listen(port, function () {
  var host = server.address().address;
  var port = server.address().port;
  console.log("Listening at http://%s:%s", host, port);
});
eurekaHelper.registerWithEureka();
