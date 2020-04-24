//
//  ColorApi.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Perez Rangel on 23/04/20.
//  Copyright © 2020 Marisol Perez Rangel. All rights reserved.
//

import Foundation
import Alamofire
import PromiseKit

class ColorApi {
    
    static let shared = ColorApi()
    
    func getColors() -> Promise<ColorService> {
        return Promise { seal in
            let endpointString = "https://reqres.in/api/register"
            guard let endpoint = URL(string: endpointString) else {
                return
            }
            AF.request(endpoint, method: .get)
            .validate()
                .responseJSON {
                    response in
                    switch response.result {
                    case .success(let json):
                        guard let json = json as? [String: Any] else {
                            return seal.reject(AFError.responseValidationFailed(reason: .dataFileNil))
                        }
                        let color = ColorService(JSON: json)
                        seal.fulfill(color!)
                    case .failure(let error):
                        print(error)
                        seal.reject(error)
                    }
            }
        }
    }
    
}
