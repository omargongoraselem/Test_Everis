import UIKit

// imprimir caracteres concatenando variables String
var hello = "Hello "
hello += "word"

// contar letras de una cadena
var countLetters = "Hello word, today is a great day"
print(countLetters.count)

// imprimir en multiples lineas
let apples = 3
let oranges = 5
let quotation = """
I said "I have \(apples) apples"
And the I said " I have \(apples + oranges) pieces of
fruit."
"""

// ARRAY
var shoppingList = [String] () // declaración de array
shoppingList = ["catfish", "water", "tulips"] //  inicializar array
shoppingList.append("chocolate")  // agregar nuevo elemento al array
shoppingList.append(contentsOf: ["potates", "milk"])

if let i = shoppingList.firstIndex(of: "water")  {
    shoppingList[i] = "bottle of water"
}
print(shoppingList) // imprimir array

shoppingList[1] = "bottle of water" // modificar un array


var fourDouble = [Double](repeating: 0.0, count: 4)

// array con key y value
var occupations = [
    "Malcom" : "Captain",
    "Kaylee" : "Mechanic"
]

occupations.updateValue("Singer", forKey:"Kaylee")
occupations["Charle"] =  "Driver"
occupations["Jayne"] = "Public Relations" // agregar nuevo elemento
occupations["Charle"] = nil
print(occupations) // imprimir array

let occupationsChar = occupations["Charle"]


var optionalString : String? = "Hello" // variable opcional
print(optionalString == nil) // comparar una variable, retorna bool

var optionalName : String?  = "Johny Bravo" // varibale opcional
var greeting = "Hello!"
if let name = optionalName {  // asignar el valor de la variable opcional a una temporal
    print("Hello, \(name)") // imprime variable temporal
} else {
    print(greeting) // si no se cumple la condición, solo imprime un saludo
}

let nickname: String? = nil // asignar a la variable ausencia de valor
let fullName: String = "Johny Apple" // valor a la variable
let informalGreeting = "Hi \(nickname ?? fullName)" // ?? Si falta el valor opcional, se utiliza el valor predeterminado.

let vegetable = "red pepper"
switch vegetable {
    case "celery":
        print("Add some raisins and make ants on a log.")
    case "cucumber", "watercress":
        print("That would make a good tea sandwich.")
    case let x where x.hasSuffix("pepper"): // Devuelve un valor booleano que indica si la cadena termina con el sufijo especificado. El sujifo es pepper
        print("Is it a spicy \(x)?") // encuentra el sufijo
    default:
        print("Everything tastes good in soup.")
}

let interestingNumbers = [
    "Primer" : [2, 3, 5, 7, 11, 13],
    "Fibonnaci" :  [1, 1, 2, 3, 4, 8],
    "Square" : [1, 4, 9, 16, 25]
]
var largest = 0

for (_, numbers) in interestingNumbers {
    for number in numbers {
        if number > largest {
            largest = number
        }
    }
}
print(largest)
