const express = require("express");

const app = express();
const server = require("http").Server(app);
const port = process.env.PORT || 8090;
const bodyParser = require("body-parser");
// var multer = require('multer');
// var multerData = multer();

app.use(express.static(__dirname + "/public"));

app.use(bodyParser.urlencoded({extended: false}));

app.use(bodyParser.json());

app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
  res.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
  next();
});

server.listen(port);

app.route("/index").get(function(req, res) {
  res.sendfile("./UI/admin.html");
});

app.route("/SCRIPT").get(function(req, res) {
  //run command
});