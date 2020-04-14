let exclamationMark : Character = "!"

let name = "Marisol Reyes"
for ch in name {
    print(ch)
}
print(name.count)

let nameChars: [Character] =  ["J", "u", "A", "n"]
var nameString = String(nameChars)
nameString.append(exclamationMark) // agregar exclamación ! a un texto

let componentName = "Marisol " + "Reyes"

let multiplier = 3
var message = "El producto de \(multiplier) x 3.5 da \(Double(multiplier)*3.5)"
message.append(exclamationMark) // agregamos el mensaje y terminamos con una exclamación

// INDICES DE STRING
let greeting = "Hola, ¿qué tal?"
greeting[greeting.startIndex]
// greeting[greeting.endIndex] ERROR AL TOMAR EL ÚLTIMO ELEMENTO, YA QUE TOMA EL ÚLTIMO PERO EL ARRAY COMIENZA EN 0, DEBERÍAMOS DE TENER UN endIndex -1
greeting[greeting.index(after: greeting.startIndex)]  // segundo caracter
greeting[greeting.index(before: greeting.endIndex)] // último caracter

for idx in greeting.indices {
    print("\(greeting[idx]) - \(idx)")
    //print("\(greeting[idx])", terminator: "") IMPRIME LA CADENA NORMAL
}

var welcome = "Hola"
welcome.insert("!", at: welcome.endIndex) // inserta al final a
welcome.insert(contentsOf: " que tal", at: welcome.index(before: welcome.endIndex))

welcome.remove(at: welcome.index(before: welcome.endIndex))
welcome
let range = welcome.index(welcome.endIndex, offsetBy: -7)..<welcome.endIndex
welcome.removeSubrange(range)
welcome

// SUBSTRING
greeting
let index = greeting.firstIndex(of: ",") ?? greeting.endIndex
let firstPart = greeting[..<index]

let newStrign = String(firstPart)

// PREFIJO Y SUFIJO
let newGreeting = "Hola soy Marisol"
newGreeting.hasPrefix("Hola")
newGreeting.hasSuffix("l")


let collection = [
    "Act 1 Scene 1", "Act 1 Scene 2", "Act 1 Scene 3", "Act 1 Scene 4", "Act 1 Scene 5",
    "Act 2 Scene 1", "Act 2 Scene 2", "Act 2 Scene 3",
    "Act 3 Scene 1", "Act 3 Scene 2"
]

var act1SceneCount = 0
var act2SceneCount = 0
var act3SceneCount = 0
var act5FinishCount = 0

for scene in collection {
    if scene.hasPrefix("Act 1") {
        act1SceneCount += 1
    }
    if scene.hasPrefix("Act 2") {
        act2SceneCount += 1
    }
    if scene.hasPrefix("Act 3") {
        act3SceneCount += 1
    }
    if scene.hasSuffix("5") {
        act5FinishCount += 1
    }
    /* NO SE PUEDE UTILIZAR SWITCH PORQUE RECORRE EL ARREGLO Y SI OCUPA EL CASO 1 YA NO PASA EL SIGUIENTE CASO
     
     switch scene {
         case let act1 where act1.hasPrefix("Act 1"):
             act1SceneCount += 1
         case let scene5 where scene5.hasSuffix("5"):
             act5FinishCount += 1
         case let act2 where act2.hasPrefix("Act 2"):
             act2SceneCount += 1
         case let act3 where act3.hasPrefix("Act 3"):
             act3SceneCount += 1
         default:
             "EL total de actos son: \(act1SceneCount + act2SceneCount + act3SceneCount)."
         }
     
     
     */
}

print("Número de escenas del acto 1 son: \(act1SceneCount)")
print("Número de escenas del acto 2 son: \(act2SceneCount)")
print("Número de escenas del acto 3 son: \(act3SceneCount)")
print("Número de escenas que terminan en 5 son: \(act5FinishCount)")
