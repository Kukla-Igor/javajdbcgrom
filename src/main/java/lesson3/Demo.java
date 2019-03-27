package lesson3;

import lesson1_2.Homework.Product;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        Product product = new Product(11, "test1", "test description11", 101);

       // productDAO.save(product);

        //System.out.println(productDAO.getProduct());

        //productDAO.update(product);

        productDAO.delete(11);
    }
}
