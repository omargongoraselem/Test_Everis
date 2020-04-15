enum PrinterError: Error {
    case outOfPaper
    case noToner
    case onFire
}

func send (job: Int, toPrinter printerName: String) throws -> String {
    if printerName == "Never Has Toner" {
        throw PrinterError.noToner
    }
    return "Job sent"
}

do {
    let printerResponse = try send(job: 1040, toPrinter: "Bi Sheng")
    print(printerResponse)
} catch {
    print(error)
}

do {
    let printerResponse = try send(job: 1040, toPrinter: "Gutenberg")
    print(printerResponse)
} catch PrinterError.onFire {
    print("I'll just put this over here, with the rest of the fire.")
} catch let printerError as PrinterError {
    print("Printer error: \(printerError)")
} catch {
    print(error)
}

let printersucess = try? send(job: 1884, toPrinter: "Megenthaler")
let printerFailure = try? send(job: 1885, toPrinter: "Never Has Toner")

var fridgeIsOpen = false
let fridgeContent = ["milk", "eggs", "leftovers"]

func fridgeContains(_ food: String) -> Bool {
    fridgeIsOpen = true
    defer {
        fridgeIsOpen = false
    }
    let result = fridgeContent.contains(food)
    return result
}
fridgeContains("banana")
print(fridgeIsOpen)

func procrastinate() {
    defer { print("lavar los platos") }
    defer { print("sacar la basura") }
    defer { print("limpiar el refrigerador") }

    print("jugar videojuegos")
}
print(procrastinate())

var a: String? = nil

func b() -> String {
    a = "Hello world"
    defer { a = nil }
    return a!
}

print(b())
