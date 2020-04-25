//
//  LoginApi.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Reyes Espino on 24/04/20.
//  Copyright © 2020 Marisol Reyes Espino. All rights reserved.
//


struct Login: Encodable {
    let email: String
    let password: String
}

import Foundation
import Alamofire
import PromiseKit

class LoginApi {
   
    /*
       let email, password: String
       
       let getDataLogin = LoginViewController()
       getDataLogin.
           
     */
    let login = Login(email: "eve.holt@reqres.in", password: "password")
    let endpointString = "https://reqres.in/api/register"

    func getUserInfo() -> Promise<LoginService> {
        return Promise { seal in
            AF.request(endpointString, method: .post, parameters: login)
                .validate()
                .responseJSON { response in
                    switch response.result {
                    case .success(let json):
                        guard let json = json  as? [String: Any] else {
                            return seal.reject(AFError.responseValidationFailed(reason: .dataFileNil))
                        }
                        let login = LoginService(JSON: json)
                        seal.fulfill(login!)
                        print(login!)
                    case .failure(let error):
                        seal.reject(error)
                    }
                }
            }
        }

}
