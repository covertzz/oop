import java.util.*;

class Main {
    public static void main(String[] args) {
        CollectionIterator i = new CollectionIterator();
       
        Object currentObj = i.next();

        while(i.hasNext()) {
            System.out.println(currentObj);
            currentObj = i.next();
        }
    }
}

class Collection {
    public ArrayList<Object> collection = new ArrayList<>();

    Collection() {
        collection.add("hello");
        collection.add("goodbye");
        collection.add(1);
        collection.add(3.7);
        collection.add(true);
    }

    public ArrayList<Object> getCollection() {
        return collection;
    }       
}

interface Iterator {
    public Object next();
    public boolean hasNext();
}

class CollectionIterator implements Iterator {
    Collection c = new Collection();
    int currentItem = 0;
    
    public Object next() {
        currentItem++;
        if(hasNext()) {
            return c.getCollection().get(currentItem-1);
        }
        return null;
    }

    public boolean hasNext() {
        return currentItem <= c.getCollection().size();
    }
}