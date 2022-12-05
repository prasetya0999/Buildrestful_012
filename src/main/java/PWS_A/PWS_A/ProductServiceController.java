/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PWS_A.PWS_A;

import java.util.HashMap;
import java.util.Map;
import model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController //membuat RestController
public class ProductServiceController {
    private static Map<String, Product> productRepo = new HashMap<>();//Membuat HasMap untuk menyimpan data pada Product.Java
    static {
        
        Product honey = new Product(); //Membuat variabel honey
        honey.setId("1"); //Memasukkan Id
        honey.setName("Honey"); //Memasukkan nama 
        productRepo.put(honey.getId(), honey);
        
        Product almond = new Product();//Membuat variabel almond
        almond.setId("2");//Memasukkan id
        almond.setName("Almond");//Memasukkan nama
        productRepo.put(almond.getId(),almond);
    }
    
    //Menampilkan page "/products"
    @RequestMapping(value = "/products")
    public ResponseEntity<Object> getProduct(){
        return new ResponseEntity<>(productRepo.values(),HttpStatus.OK);
    }
    
    //Untuk Menambahkan data pada page "/products"
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        //Jika ID yang akan di tambahkan sudah ada maka data tidak akan berubah
        if(productRepo.containsKey(product.getId())){
            return new ResponseEntity<>("ID Product is Exist",HttpStatus.CONFLICT);
        }
        else {//Jika data yg akan di tambahkan belum ada maka akan sukses
            productRepo.put(product.getId(), product);
            return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);   
        }    
    }
    
    //Untuk Mengedit data pada page "/products"
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product){
        //jika hendak mengedit data tidak akan berjalan apabila ID Product tidak ada
        if(!productRepo.containsKey(id)){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        else {//Edit akan berhasil jika ID yang dimasukkan benar
            productRepo.remove(id);
            product.setId(id);
            productRepo.put(id, product);
            return new ResponseEntity<>("Product is update seccessfully", HttpStatus.OK);
        }
    }
    
    //Untuk Menghapus data pada page "/products"
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id)
    {
        productRepo.remove(id);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
    }
}