//
//  ViewController.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Perez Rangel on 17/04/20.
//  Copyright © 2020 Marisol Perez Rangel. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
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
        
        URLSession.shared.dataTask(with: endpoint) { (data: Data?, _, error: Error?) in
            if error != nil {
                print("error")
                return
            }
            guard let dataFromService = data, let dictionary = try? JSONSerialization.jsonObject(with: dataFromService, options: []) as? [String: Any] else {
                return
            }
            print(dictionary["name"] as? String ?? "error2")
        }
    }
    
    
    
    
}

