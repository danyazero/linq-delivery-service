package org.zero.npservice.mapper;

import org.zero.npservice.model.UserData;
import org.zero.npservice.model.delivery.ContactPerson;

public class ContactPersonMapper {
  public static ContactPerson map(UserData userData) {
    return new ContactPerson(
        userData.firstName(), userData.lastName(), userData.middleName(), userData.phone(), false);
  }
}
