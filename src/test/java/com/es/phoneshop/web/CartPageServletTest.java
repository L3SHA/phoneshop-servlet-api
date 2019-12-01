package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @Mock
    private Product product;
    @InjectMocks
    private CartPageServlet servlet;

    @Before
    public void setup() {
        when(productDao.getProduct(anyLong())).thenReturn(product);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getRequestDispatcher("/WEB-INF/pages/cart.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        String[] productIdStrings = {"1", "2"};
        String[] quantityStrings = {"1", "2"};
        when(request.getParameterValues("productId")).thenReturn(productIdStrings);
        when(request.getParameterValues("quantity")).thenReturn(quantityStrings);
        when(request.getLocale()).thenReturn(Locale.ENGLISH);

        servlet.doPost(request, response);

        verify(response).sendRedirect(request.getRequestURI() + "?success=true");
    }

    @Test
    public void testDoPostWrongParameters() throws ServletException, IOException {
        String[] productIdStrings = {"1", "2"};
        String[] quantityStrings = {"-1", "wrongValue"};
        when(request.getParameterValues("productId")).thenReturn(productIdStrings);
        when(request.getParameterValues("quantity")).thenReturn(quantityStrings);
        when(request.getLocale()).thenReturn(Locale.ENGLISH);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }
}