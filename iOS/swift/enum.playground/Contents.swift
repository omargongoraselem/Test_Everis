enum Rank: Int { //declarando enumeración con tipo de dato
    case ace = 1 // comenzar con la enumeración
    case two, three, four, five, six, seven, eight, nine, ten // siguiendo la enumeración
    case jack, queen, king // siguiendo la enumeración
    
    func simpleDescription() -> String { // declarando la función
        switch self {
        case .ace: // tipo de caso
            return "ace" // imprimir
        case .jack:
            return "jack"
        case .queen:
            return "queen"
        case .king:
            return "king"
        default:
            return String(self.rawValue)
        }
    }
}
let ace = Rank.ace // tipo de caso que vamos a tomar (ace)
let aceRawValue = ace.rawValue // imprime el valor que corresponde al caso (1)

let two = Rank.two // tipo de caso que vamos a tomar (two)
let twoRawValue = two.rawValue // imprime el valor que corresponde al caso (2)

let queen = Rank.queen // tipo de caso que vamos a tomar (queen)
let queenRawValue = queen.rawValue // imprime el valor que corresponde al caso (12)

if let convertedRank = Rank(rawValue: 3) { // ingresando con el valor del caso (3)
    //let threeDesccription = convertedRank.simpleDescription() Esto equivale a esto
    _ = convertedRank.simpleDescription()

}

enum Suit { // inicializando num sin tipo de dato
    case spades, hearts, diamonds, clubs // tipos de caso
    
    func simpleDescript() -> String {
        switch self {
        case .spades: // caso (spades)
            return "spades black" // imprime (spades black)
        case .hearts: // caso (hearts)
            return "hearts black" // imprime (hearts black)
        case .diamonds: // caso (diamonds)
            return "diamonds red" // imprime (diamonds red)
        case .clubs: // caso (clubs)
            return "clubs black" // imprimr (clubs black)
        }
    }
}

let hearts = Suit.hearts // seleccionando el tipo de caso
let heartsDescription = hearts.simpleDescript() // imprime la descripcion dependiendo el caso (hearts black)

enum ServerResponse { // declarando num sin tipo de dato
    case result(String, String) // caso
    case failure(String) // caso
}

let success = ServerResponse.result("6:00 am", "8:09 pm") // declarando caso con parametros
let failure = ServerResponse.failure("Out of cheese") // declarando caso con parametro

switch success { //
case let .result(sunrise, sunset):
    print("Sunrise is at \(sunrise) and sunset is at \(sunset)")
case let .failure(message):
    print("Failure... \(message)")
}
// declarando una estructura de acuerdo a los num declarados anteriormente
struct Card {
    var rank: Rank
    var suit: Suit
    
    func simpleDescriptions() -> String {
        return "The \(rank.simpleDescription()) of \(suit.simpleDescript())"
    }
}
let threeOfSpades = Card(rank: .three, suit: .spades)
let threeOfSpadesDescription = threeOfSpades.simpleDescriptions()
