//
//  LoginService.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Perez Rangel on 21/04/20.
//  Copyright © 2020 Marisol Perez Rangel. All rights reserved.
//

import Foundation
import ObjectMapper

class LoginService: Mappable {
    required init?(map: Map) {
        
    }
    
    func mapping(map: Map) {
        id <- map["id"]
        token <- map["token"]
    }
    
    var id: Int?
    var token: String?
    
}
