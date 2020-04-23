//
//  LoginService.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Reyes Espino on 21/04/20.
//  Copyright © 2020 Marisol Reyes Espino. All rights reserved.
//

import Foundation
import ObjectMapper

class LoginService: Mappable {

    var id: Int!
    var token: String!
    
    required init?(map: Map) { }
    
    func mapping(map: Map) {
        id <- map["id"]
        token <- map["token"]
    }
    
}

