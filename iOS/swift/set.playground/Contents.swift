var colors = Set<String> ()
colors = ["red", "blue", "orange"]
colors.insert("black")
colors

if colors.contains("red") {
    colors.remove("orange")
}
colors
colors.insert("black")

let oddDigits: Set = [1,3,5,7,9]
let evenDigits: Set = [0,2,4,6,8]
let primeNumbers: Set = [2,3,5,7]

// A UNIÓN B = elementos que son o bien de A, o bien de B o de los dos
oddDigits.union(evenDigits).sorted()
// A INSERCCIÓN B = elementos que son a la vez de A y de B
oddDigits.intersection(evenDigits) // no existe un elemento par e impar
// De los impares cuales son primos
evenDigits.intersection(primeNumbers).sorted()
// De los pares cuales son primos
oddDigits.intersection(primeNumbers).sorted()

// Diferencia de conjuntos A - B = elementos que son de A pero no de B
// Los de A les resto los de B
oddDigits.subtracting(primeNumbers).sorted() // Todos los numeros pares que no son primos

// Diferencia simetrica
// A + B = ( A - B ) UNION ( B - A )
oddDigits.symmetricDifference(primeNumbers).sorted() // Los numeros pares que no son primos y los primos que no son pares

// Un conjunto es subconjunto de otro cuando esta adentro de otro
// Dos disjuntos se dice cuando no tienen elementos en comun
// Si el grande contiene al pequeño es super conjunto del pequeño

let houseAnimals: Set = ["🐶"]
let farmAnimal: Set = ["🦓", "🐶"]
let cityAnimal: Set = ["🐘", "🐬"]

houseAnimals.isSubset(of: farmAnimal)
farmAnimal.isSubset(of: houseAnimals)
// A y B son disjuntos si su insercción es vacia 
farmAnimal.isDisjoint(with: cityAnimal)
