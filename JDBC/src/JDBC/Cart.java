package JDBC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Cart extends JPanel{
	public RegistrationApplication mainApp;
	private CartService cartService;
	private JFrame frame;
	private JTextField userIdField, productIdField, quantityField, priceField;
	private DefaultTableModel tableModel;
	private JTable cartTable;
	private List<CartItem> result;
	public Cart(RegistrationApplication mainApp) {
		this.mainApp = mainApp;
		cartService = new CartService(mainApp);
		mainApp.setTitle("Shopping Cart");
		createUI();
	}
	
	private void createUI() {
		
		JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

//        inputPanel.add(new JLabel("User ID:"));
//        userIdField = new JTextField();
//        inputPanel.add(userIdField);

        inputPanel.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        inputPanel.add(productIdField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Price per Unit:"));
        priceField = new JTextField();
        inputPanel.add(priceField);
		add(inputPanel);
		
		JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2));

        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(new AddProductListener());
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Update Quantity");
        updateButton.addActionListener(new UpdateQuantityListener());
        buttonPanel.add(updateButton);

        JButton viewButton = new JButton("View Cart");
        viewButton.addActionListener(new ViewCartListener());
        buttonPanel.add(viewButton);

        JButton removeButton = new JButton("Remove Product");
        removeButton.addActionListener(new RemoveProductListener());
        buttonPanel.add(removeButton);
        
        add(buttonPanel);
        
        String[] columnNames = {"Cart ID", "Product ID", "Quantity", "Total Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(tableModel);
        tableModel.addRow(columnNames);
//        JScrollPane tableScrollPane = new JScrollPane(cartTable);
        add(cartTable);
	}
	
	private class AddProductListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
			int productId = Integer.parseInt(productIdField.getText());
			int quantity = Integer.parseInt(quantityField.getText());
			double price = Double.parseDouble(priceField.getText());
			
			cartService.addProduct(productId, quantity, price);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
			
		}

	}
	
	private class UpdateQuantityListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int productId = Integer.parseInt(productIdField.getText());
				int quantity = Integer.parseInt(quantityField.getText());
				double price = Double.parseDouble(priceField.getText());
				
				cartService.updateProductQuantity(productId, quantity, price);
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			
		} 
		
	}
	
	private class ViewCartListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
			
				result = cartService.getCartProducts();
				tableModel.setRowCount(1);
				for(CartItem item : result) {
				    tableModel.addRow(new Object[]{item.getCartId(), item.getProductId(), item.getQuantity(), item.getTotalPrice()});
				}
		}
		
	}
	
	private class RemoveProductListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int productId = Integer.parseInt(productIdField.getText());
			
			if(cartService.removeProduct(productId)) {
				tableModel.setRowCount(1);
				result = cartService.getCartProducts();
				for(CartItem item : result) {
				    tableModel.addRow(new Object[]{item.getCartId(), item.getProductId(), item.getQuantity(), item.getTotalPrice()});
				}
			} else {
				JOptionPane.showMessageDialog(mainApp, "Product Not found");
			}
		}
		
	}
}
