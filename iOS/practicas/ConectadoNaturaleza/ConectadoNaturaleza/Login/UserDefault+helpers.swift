//
//  segueLogin.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Reyes Espino on 28/04/20.
//  Copyright © 2020 Marisol Reyes Espino. All rights reserved.
//

import UIKit

extension UserDefaults {
    
    enum UserDefaultsKeys: String {
        case isLoggedIn
    }
    func setIsLogedIn(value: Bool) {
        set(false, forKey: UserDefaultsKeys.isLoggedIn.rawValue)
        synchronize()
    }
    
    func isLoggedIn() -> Bool {
        return bool(forKey: UserDefaultsKeys.isLoggedIn.rawValue)
    }
}
