var express = require('express');
var router = express.Router();


/*const SerialPort = require('serialport');
const port = new SerialPort('COM3', {
    baudRate: 9600
});
*/
router.get('/', function (req, res) {
    var db = req.db;

    getAllMelodies(db, function (melodies) {
        res.render('android', { title: 'Android', data: melodies });
    });
});

router.get('/getAllMelodies', function (req, res) {
    var db = req.db;

    getAllMelodies(db, function (notes) {
        res.json(notes);
    });
});

router.get('/stopMelode', function (req, res) {
    var db = req.db;

    port.write('#', function (err) {
        if (err) {
            return console.log('Error on write: ', err.message);
        }
        res.status(200).send("Done");
    });
});

router.get('/playMelode/:id', function (req, res) {
    var id = req.params.id;
    var db = req.db;

    if (id === null || isNaN(id)) {
        res.status(501).send("Wrong parameter!");
    }
    else {
        getMelode(db, id, function (melode) {
            console.log(melode[0]);
                console.log("Playing melody");
                port.write(melode[0].natos, function (err) {
                    if (err) {
                        console.log("yer error");
                        return console.log('Error on write: ', err.message);
                    }
                    else {
                        console.log("no error");
                        return res.status(200).send("Done");
                    }
                });
        });
    }
});

router.post("/addMelody", function(req, res){
    var db = req.db;
    var name = req.body.name;
    var notes = req.body.notes;
    saveNote(name, notes, db, function(err){
       console.log("Saving melody");
       if(err){
           res.status(500).send("Error");
       }
       else{
           res.status(200).send("Done");
       }
    });
});

router.get("/deleteMelody/:id", function(req, res){
   var db = req.db;
   var id = req.params.id;
   console.log(id);
   deleteNote(id, db, function(err){
      if(err){
          res.status(500).send("Error");
      }
      else{
          res.status(200).send("Done");
      }
   });
});

module.exports = router;

function deleteNote(id, db, callback)
{
    db.run("delete from melodijos where id = ? ", id, function(deleteError)
    {
        //jei nebuvo jokios klaidos deleteError=null
        //jei buvo klaida deleteError=klaidos_pranesimas
        return callback(deleteError);
    });
}

function getAllMelodies(db, callback) {
    db.all('select melodijos.id, melodijos.pavadinimas, melodijos.natos FROM melodijos;', function (err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        }
        else {
            return callback(rows);
        }
    });
}

function getMelode(db, id, callback) {
    db.all('select natos FROM melodijos WHERE id = ?', id, function (err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        }
        else {
            return callback(rows);
        }
    });
}

function saveNote(title, text, db, callback)
{
    var sqlInsert = "insert into melodijos (pavadinimas, natos) values ( ";
    sqlInsert += "'" + title + "', ";
    sqlInsert += "'" + text + "'); ";

    db.run(sqlInsert, function(insertError)
    {
        return callback(insertError);
    });
}