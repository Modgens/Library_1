package entity.megaEntity;

public class PersonFactory {
    public Person createPerson(PersonType type) {
        Person person = null;

        switch (type) {
            case USER:
                person = new MegaUser();
                break;
            case ADMIN:
                person = new MegaAdmin();
                break;
            case LIBRARIAN:
                person = new MegaLibrarian();
                break;
        }
        return person;
    }
}
