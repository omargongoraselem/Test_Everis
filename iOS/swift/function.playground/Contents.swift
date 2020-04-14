import UIKit

func sayHello(personName: String) -> String {
    let greeting = "Hello, " + personName  + " ! "
    return greeting
}

print(sayHello(personName: "Jhony"))

func greet(person: String, day:String) -> String {
    return "Hello \(person), today is \(day)."
}

greet(person: "Jhonyto", day: "Monday")

func greet(_ person: String, on day: String) -> String {
    return "Hello \(person), today is \(day)"
}

greet("John", on: "Sunday")

func calculateStatics(scores: [Int]) -> (min: Int, max: Int, sum: Int) {
    var min = scores[0]
    var max = scores[0]
    var sum = 0
    
    for score in scores {
        if score > max {
            max = score
        } else if score < min {
            min = score
        }
        sum += score
    }
    return (min, max, sum)
}

let statics = calculateStatics(scores: [5, 3, 100, 3, 9])
print(statics.sum)
print(statics.0)
print(statics.1)
print(statics.2)

func rangeLenght(start: Int, end: Int) -> Int {
    return end - start
}
print(rangeLenght(start: 2, end: 7))

var decimal: Double = 8.0
decimal.formTruncatingRemainder(dividingBy: 3)
print(decimal)

func returnFifteen() -> Int {
    var y = 10
    func add() {
        y += 5
    }
    add()
    return  y
}
returnFifteen()

func makeIncrementer() -> ((Int) -> Int) {
    func addOne(number: Int) -> Int {
        return 1 + number
    }
    return addOne
}

var increment = makeIncrementer()
increment(7)

func hasAnyMatches(list: [Int], condition: (Int) -> Bool) -> Bool {
    for item in list {
        if condition(item) {
            return true
        }
    }
    return false
}

func lessThanTen(number: Int) -> Bool {
    return number < 10
}

var numbers = [20, 19, 7, 12]
hasAnyMatches(list: numbers, condition: lessThanTen)

numbers.map({ (number: Int) -> Int in
    let result = 3 * number
    return result
})

let mappedNumbers = numbers.map({ number in 4 * number })
print(mappedNumbers)

let sortedNumbers = numbers.sorted { $0 < $1 }
print(sortedNumbers)
