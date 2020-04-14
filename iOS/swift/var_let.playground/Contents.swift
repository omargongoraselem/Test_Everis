import UIKit

let maximumNumberOfLoginAttempts = 3
var currentLoginAttempt = 0
var x = 0.0, y = 0.0, z = 0.0
var  welcomeMessage :  String
var red, green,  blue : Double
    
welcomeMessage = "Hola, bienvenido!"

let π = 3.141516
print(1.0, 2.0, 3.0, 4.0, 5.0, separator: " ... ", terminator: "!")
print("El número  de login  actual es: \(currentLoginAttempt)")

for n in 1...5 {
    print(n, terminator: "!")
}
