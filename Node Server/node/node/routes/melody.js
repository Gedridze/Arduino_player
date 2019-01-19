var express = require('express');
var router = express.Router();

router.get('/new', function(req, res)
{
    var db = req.db;

    res.render('melody_new', { title: 'Nauja Melodija'});
});

router.post('/new', function(req, res)
{
    var db = req.db;

    var title = req.body.title;
    var text = req.body.text;

    if(title.length > 0 && text.length > 0)
    {

        saveNote(title, text, db, function(rez)
        {
            if(rez == null)
            {
                res.render('melody_new', { title: 'Nauja Melodija', info:"Melodija išsaugota!" });
            }
            else
            {
                res.render('melody_new', { title: 'Nauja Melodija', info:"Melodija neišsaugota!" });
            }
        });

    }
    else
    {
        res.render('note_new', { title: 'Naujas Užrašas', info:"Įveskite trūkstamus duomenis!" });
    }
});

router.get('/:id', function(req, res)
{
    var id = req.params.id;
    var db = req.db;

    getMelode(id, db, function(melode)
    {
        res.render('melody', { title: 'Melodija', data:melode });
    });
});

router.get('/delete/:id', function(req, res)
{
    var id = req.params.id;
    var db = req.db;
    getMelode(id, db, function(melode)
    {
        if(melode != null)
        {
            res.render('melody_delete', { title: 'Melodijos trynimas', data: melode, melode_id:melode.id });
        }
        else
        {
            res.render('melody_delete', { title: 'Melodijos trynimas', info: "Melodija nerasta!", melode_id:0 });
        }
    });
});

router.post('/delete/:id', function(req, res)
{
    var id = req.params.id;
    var db = req.db;
    getMelode(id, db, function(melode)
    {
        if(melode != null)
        {
            deleteNote(id, db, function(rez)
            {
                var msg = "";
                if(rez == null)
                {
                    msg = 'Užrašas "' + melode.pavadinimas + '" ištrintas!';
                }
                else
                {
                    msg = 'Užrašas "' + melode.pavadinimas + '" neištrintas!';
                }

                res.render('melody_delete', { title: 'Melodijos trynimas', data: melode, info: msg });
            });
        }
        else
        {
            res.render('melody_delete', { title: 'Melodijos trynimas', info: "Melodija nerasta!", melode_id:0 });
        }
    });
});

router.get('/edit/:id', function(req, res)
{
    var id = req.params.id;
    var db = req.db;
    getMelode(id, db, function(melode)
    {
        if(melode != null)
        {
            res.render('melody_edit', { title: 'Melodijos redagavimas', data: melode });
        }
        else
        {
            res.render('melody_edit', { title: 'Melodijos redagavimas', info: "Melodija nerasta!" });
        }
    });
});

router.post('/edit/:id', function(req, res)
{
    var db = req.db;

    var title = req.body.title;
    var text = req.body.text;
    var id = req.params.id;

    if(id != null)
    {
        if(title.length > 0 && text.length > 0)
        {
            updateNote(id, title, text, db, function(rez)
            {
                if(rez == null)
                {
                    getMelode(id, db, function(melode)
                    {
                        if(melode != null)
                        {
                            res.render('melody_edit', { title: 'Melodijos redagavimas', data: melode, info:"Melodija atnaujinta!" });
                        }
                        else
                        {
                            res.render('melody_edit', { title: 'Melodijos redagavimas', info: "Melodija nerasta!" });
                        }
                    });
                }
                else
                {
                    res.render('melody_edit', { title: 'Melodijos redagavimas', info:"Melodija neatnaujinta!" });
                }
            });

        }
        else
        {
            res.render('melody_edit', { title: 'Melodijos redagavimas', info:"Įveskite trūkstamus duomenis!" });
        }
    }
    else
    {
        res.render('melody_edit', { title: 'Melodijos redagavimas', info: "Melodija nerasta!" });
    }
});

module.exports = router;

function getMelode(id, db, callback)
{
    db.all('select * FROM melodijos WHERE id = ?;', id, function(err,rows)
    {
        if(err)
        {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        }
        else
        {
            return callback(rows[0]);
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

function deleteNote(id, db, callback)
{
    db.run("delete from melodijos where id = ? ", id, function(deleteError)
    {
        //jei nebuvo jokios klaidos deleteError=null
        //jei buvo klaida deleteError=klaidos_pranesimas
        return callback(deleteError);
    });
}

function updateNote(noteId, title, text, db, callback)
{
    db.run("update melodijos set pavadinimas = ?, natos = ? where id = ? ;", title, text, noteId, function(updateError)
    {
        //jei nebuvo jokios klaidos updateError=null
        //jei buvo klaida updateError=klaidos_pranesimas
        return callback(updateError);
    });
}