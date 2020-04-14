var n = 2
while n < 100 {
    n *= 2
}
print(n)

var m = 2
repeat {
    m *= 2
} while m < 100
    print(m)

var total = 0
for i in 0..<4 {
    total += i
}
print(total)


let names  = ["Mar", "Mari", "Sol"]
for i in 0..<names.count {
    print("La primer persona \(i+1) es \(names[i])")
}

for name in names[1...] {
    print(name)
}

for name in names[..<2] {
    print(name)
}
