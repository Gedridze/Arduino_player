function CheckData() {
    var melodija = document.getElementById("text").value;
    var natosPat = /\s[ABCDEFG][1-6]/;
    var ilgiaiPat = /\s[1-9]/;
    var simboliai = melodija.split('');
    if (!isNaN(simboliai[0]) && simboliai[0] >= 1 && simboliai[0] <= 50) {
        var k = simboliai[0]*3+1;
        for (i = 1; i <= simboliai[0] * 3; i += 3){
            if (!natosPat.test(melodija.substr(i, 3))){
                Swal.fire('Klaida...', 'Neteisingai įvesta melodija!', 'error');
                return false;
            }
            if (!ilgiaiPat.test(melodija.substr(k, 2))) {
                Swal.fire('Klaida...', 'Neteisingai įvesta melodija!', 'error');
                return false;
            }
            k += 2;
        }
        if (!(simboliai[k].valueOf() === new String(",").valueOf())){
            Swal.fire('Klaida...', 'Neteisingai įvesta melodija!', 'error');
            return false;
        }
    }
    else {
        Swal.fire('Klaida...', 'Neteisingai įvesta melodija!', 'error');
        return false;
    }
}