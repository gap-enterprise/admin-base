/*
 * Copyright (c) 2022 Surati

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
	
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.admin.base.api;

/**
 * Person.
 * 
 * @since 0.1
 */
public interface Person {
	
	/**
	 * Get identifier of a person
	 * @return identifier
	 */
	Long id();
	
	/**
	 * Full name of a person
	 * @return name
	 */
	String name();

	/**
	 * Get address.
	 * @return Address
	 */
	String address();

	/**
	 * Get P.O. Box
	 * @return P.O. Box
	 */
	String pobox();

	/**
	 * Get phone.
	 * @return Phone
	 */
	String phone();

	/**
	 * Get email.
	 * @return E-mail
	 */
	String email();
	
	/**
	 * Modify name of a person
	 * @param name
	 */
	void update(String name);

	/**
	 * Set contacts.
	 * @param address Address
	 * @param pobox P.O. Box
	 * @param phone Phone
	 * @param email Email
	 */
	void contacts(String address, String pobox, String phone, String email);
}
