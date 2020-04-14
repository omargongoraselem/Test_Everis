import UIKit

let age : UInt8 = 31
let minValue = UInt8.min
let maxValue = UInt8.max

let minValue32 = UInt16.min
let maxValue32 = UInt16.max

print(maxValue)
print(minValue32)
print(maxValue32)

let f1 : Float = 3.14159265
let d1 : Double = 3.14159265
let pi  =  3.14159265
let anotherPi = 3 + 0.14159
let decimalInteger = 17
let binaryInteger = 0b10001 // 1*2^4+0*2
let octalInteger = 0o21
let hexadecimalInteger = 0x11

let  paddedDouble = 000123.456
let someNumber = 000000097.540

let oneMillion = 1_000_000
let justMoreThanAMillion = 1_000_000.000_000_1

//let cannotBeNegative : UInt8 = -1
//let tooBig : UInt8 = UInt8.max + 1

let twoThousand: UInt16 = 2_000
let one : UInt8 = 1
let twoThousandAndOne = twoThousand + UInt16(one)

let three = 3
let decimalNumber = 0.14159
let pinNumber = Double(three) + decimalNumber  // casting

let integerPi = three + Int(decimalNumber)

typealias audioSample = UInt16
var maxAmplitude =  audioSample.max


// BOOLEAN

let orangesAreOrange = true
let foodIsDelicious = false

var isAge : Bool
isAge = false

if isAge {
    print("Puedes ingresar a la fiesta")
} else {
    print("No puedes ingresar a la fiesta")
}

var ifAge = 12
if ifAge >= 18 {
    print("Puedes ingresar a la fiesta")
}

//tuplas
let http404Error = (404, "Página no encontrada")
let (statusCode, statusMessage) = http404Error
print("El código del estado es \(statusCode)")
print("El código del mensaje es \(statusMessage)")

let (justStatusCode, _) = http404Error
print("El código del estado es \(justStatusCode)")

print("El código del error es \(http404Error.0) y el mensaje es \(http404Error.1)")

let http200Status = (statusCode: 200, description: "OK")
print("El código del estado es \(http200Status.statusCode) y el mensaje es \(http200Status.description)")

let myInformation = (name: "Marisol", age: 25, lastName: "Reyes")
print("Mi nombre es \(myInformation.name) \(myInformation.lastName) y tengo \(myInformation.age) años")
print(myInformation.0)

var server : Int? = 404
server = nil
print(server)

let possibleAge = "31"
let convertedAge = Int(possibleAge)

if convertedAge != nil {
    print("La edad ingresada es \(convertedAge!)")
} else {
    print("La edad es  null")
}

if let firstNumber = Int("4"),
    let secondNumber = Int("42"),
    firstNumber < secondNumber && secondNumber < 100 {
        print("\(firstNumber) < \(secondNumber) < 100")
}

let possibleString : String? = "Un String opcional"
let forcedString : String = possibleString!

let assumedString : String! = "Un String unwrapped de forma implicita a partir de un opcional"
let implicitString : String = assumedString

if assumedString != nil {
    print(assumedString!)
}
if let definitiveString = assumedString {
    print(definitiveString)
}
print(assumedString)
