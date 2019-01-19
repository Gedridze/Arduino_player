var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var melody = require('./routes/melody');
var usersRouter = require('./routes/users');
var android = require('./routes/android');
const SerialPort = require('serialport');
port = new SerialPort('COM3', {
    baudRate: 9600
});


var app = express();

var sqlite3 = require('sqlite3');

/*** db aprasymas ***/
var db = new sqlite3.Database('./data/melodijos.db');

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));


app.use(function(req, res, next)
{
    req.db = db;
    next();
});


app.use('/', indexRouter);
app.use('/melody', melody);
app.use('/users', usersRouter);
app.use('/android', android);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;

