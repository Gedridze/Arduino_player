var express = require('express');
var router = express.Router();



router.get('/', function(req, res)
{
    var db = req.db;

    getAllMelodies(db, function(melode)
    {
        res.render('index', { title: 'Melodijos', data:melode });
    });
});

router.get('/stopMelodeWeb', function (req, res)
{
    var db = req.db;

    port.write('#', function (err) {
        if (err) {
            return console.log('Error on write: ', err.message);
        }
        res.status(200).send("Done");
    });
    res.redirect('/');
});

router.get('/play/:id', function (req, res) {
    var id = req.params.id;
    var db = req.db;

    getMelode(db, id, function (melode) {
            port.write(melode[0].natos, function (err) {
                if (err) {
                    return console.log('Error on write: ', err.message);
                }
            })
        res.render('melody_play', { title: 'Melodijos grojimas', data:melode });
    });
});

module.exports = router;

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
    db.all('select * FROM melodijos WHERE id = ?;', id, function (err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        }
        else {
            return callback(rows);
        }
    });
}