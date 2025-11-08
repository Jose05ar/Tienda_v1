/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.service;

import com.tienda.domain.Producto;
import com.tienda.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author erick
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository; //CRUD R=READ C=CREATE U=UPDATE D=DELETE C-> CREATE(INGRESA A LA BD Y VOY A CREAR NUEVAS CATEGORIAS)

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) {//Read --> Ingresa a la bd pueda leer toda la información de la producto
        var lista = productoRepository.findAll();
        if (activo) {
            lista.removeIf(e -> !e.getActivo());
        }
        return lista;
    }

    @Transactional
    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    @Transactional
    public boolean delete(Producto producto) { //eliminar un producto
        try {
            productoRepository.delete(producto);
            productoRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        return productoRepository.findById(producto.getIdProducto()).orElse(null);

    }

    //Lista de productos con precio inferior y superior para la consulta ampliada 
    @Transactional(readOnly = true)
    public List<Producto> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup) {
        return productoRepository.findByPrecioBetweenOrderByDescripcion(precioInf, precioSup);
    }
    
    //Lista de roductos con precio inferior y superior para la consulta JPAL
    @Transactional(readOnly=true)    
    public List<Producto> metodoJPQL(double precioInf, double precioSup) {
        return productoRepository.metodoJPQL(precioInf, precioSup);
    }
    

}
