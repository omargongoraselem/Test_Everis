//
//  ColorService.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Reyes Espino on 23/04/20.
//  Copyright © 2020 Marisol Reyes Espino. All rights reserved.
//

import Foundation
import ObjectMapper

class ColorService: Mappable {
    
    var colors : [Color]?
    
    required init?(map: Map) {
    }
    
    func mapping(map: Map) {
        colors <- map["data"]
    }
}

class Color: Mappable {
    
      var id: Int?
      var name: String?
      var year: Int?
      var color: String?
      var pantone_value: String?
      
      required init?(map: Map) { }
      
      func mapping(map: Map) {
          id <- map["id"]
          name <- map["name"]
          year <- map["year"]
          color <- map["color"]
          pantone_value <- map["pantone_value"]
      }
}
