//
//  ViewController.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Perez Rangel on 17/04/20.
//  Copyright © 2020 Marisol Perez Rangel. All rights reserved.
//

struct Login: Encodable {
    let email: String
    let password: String
}

import UIKit
import Alamofire

class ViewController: UIViewController {
    
    @IBAction func ingresaButton(_ sender: Any) {
        fetchService()
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    private func fetchService() {
        
        let endpointString = "https://reqres.in/api/register"
        guard let endpoint = URL(string: endpointString) else {
            return
        }
        
        let login = Login(email: "eve.holt@reqres.in", password: "password")
        
        AF.request(endpoint, method: .post, parameters: login, encoder: JSONParameterEncoder.default).response {
            response in debugPrint(response)
            if let data = response.data {
                let json = String(data: data, encoding: String.Encoding.utf8)
                print("JSON Response: \(String(describing: json))")
                let loginService = LoginService(JSONString: json!)
                print(loginService?.id)
                print(loginService?.token)
            }
        }
    }
}

