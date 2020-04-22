//
//  ViewController.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Reyes Espino on 17/04/20.
//  Copyright © 2020 Marisol Reyes Espino. All rights reserved.
//

struct Login: Encodable {
    let email: String
    let password: String
}

import UIKit
import Alamofire
import PromiseKit

class ViewController: UIViewController {
    
    @IBAction func ingresaButton(_ sender: Any) {
        getUserInfo()
        .done { json -> Void in
            print(json)
        }
        .catch { error in
            print(error.localizedDescription)
        }
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    
    let login = Login(email: "eve.holt@reqres.in", password: "password")
    let endpointString = "https://reqres.in/api/register"
    
    func getUserInfo() -> Promise<[String: Any]> {
        return Promise { seal in
            AF.request(endpointString, method: .post, parameters: login)
                .validate()
                .responseJSON { response in
                    switch response.result {
                    case .success(let json):
                        guard let json = json  as? [String: Any] else {
                            return seal.reject(AFError.responseValidationFailed(reason: .dataFileNil))
                        }
                        seal.fulfill(json)
                    case .failure(let error):
                        seal.reject(error)
                    }
                }
            }
        }
}


