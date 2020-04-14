let age = -5
// assert(age >= 0, "La edad no puede ser negativa")
// precondition(age >= 0, "La edad no puede ser negativa")

if age > 10 {
    print("Puedes subir a la montaña rusa")
} else if age >=  0 {
    print("Eres demasiado pequeño para subir a la montaña")
} else {
    //assertionFailure("La edad de una persona no puede ser negativa ")
    preconditionFailure("La edad no puede ser negativa")
}

/*
 Los assert, preconditions son funciones para detener un programa, teniendo un mensaje en respuesta por detener el programa
 Existen preconditions y asserts que se pueden definir Failure para saber en donde se detuvo el programa
 */
