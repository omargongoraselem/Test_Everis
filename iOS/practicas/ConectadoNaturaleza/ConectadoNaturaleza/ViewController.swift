//
//  ViewController.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Perez Rangel on 17/04/20.
//  Copyright © 2020 Marisol Perez Rangel. All rights reserved.
//

struct Login: Codable {
    let page: String
    let per_page: Int
}

import UIKit
import Alamofire
// import AlamofireObjectMapper

class ViewController: UIViewController {
    
    @IBOutlet weak var userLabel: UILabel!
    @IBOutlet weak var passwordLabel: UILabel! 

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        fetchService()
    }
    
    // Endpoint: https://reqres.in/api/register
    // 1. Crear excepcion de seguridad.
    // 2. Crear URL con el endpoint
    // 3. Hacer request con la ayuda de URLSession
    // 4. Transformar respuesta a diccionario
    // 5. Ejecutar request
    
    private func fetchService() {
        let endpointString = "https://reqres.in/api/register"
        guard let endpoint = URL(string: endpointString) else {
            return
        }
        
        // let login = Login(email: "eve.holt@reqres.in", password: "password")
        
        AF.request(endpoint, method: .post, parameters: nil).responseData { (response: AFDataResponse<Data>) in
            if response.error != nil {
                print("error")
                return
            }
            guard
                let dataFromService = response.data,
                let model = try? JSONDecoder().decode(Login.self, from: dataFromService)
                
            else {
                    return
                }
            
            DispatchQueue.main.async {
                let stringPage = String(model.page)
                print(stringPage)
                self.userLabel.text = stringPage
                //self.passwordLabel.text = model.age
            }
            //debugPrint(response)
        }
    }
}

