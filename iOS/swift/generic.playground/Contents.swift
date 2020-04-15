func makeArray<Item>(repeating item: Item, numberIfTimes: Int) -> [Item] {
    var result = [Item]()
    for _ in 0..<numberIfTimes {
        result.append(item)
    }
    return result
}

makeArray(repeating: "knock", numberIfTimes: 4)

enum OptionalValue<Wrapped> {
    case none
    case some(Wrapped)
}

var posibleInteger: OptionalValue<Int> = .none
posibleInteger = .some(100)

func anyCommonElement<T: Sequence, U: Sequence>(_ lhs: T, _ rhs: U) -> Bool
    where T.Element: Equatable, T.Element == U.Element {
        for lhsItem in lhs {
            for rhsItem in rhs {
                if lhsItem == rhsItem {
                    return true
                }
            }
        }
        return false
    }

anyCommonElement([1,2, 3], [3])
