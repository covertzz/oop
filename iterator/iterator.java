/*
 * Authors: Palmer Du, Jason Yam
 * Class: CS3560 Object Oriented Programming
 * Description: This program showcases the iterator design method which involves the implementation 
 *              of next() and hasNext() methods to cycle through a collection of objects.
 */

import java.util.*;

//Main executes our iterator class design
class Main {
    public static void main(String[] args) {
        CollectionIterator i = new CollectionIterator();
        Object currentObj; //Create CollectionIterator object 

        //While loop runs while CollectionIterator contains objects
        while(i.hasNext()) { 
            currentObj = i.next();
            System.out.println(currentObj); //Iterate through next object 
             //Print out object
        }
    }
}

class Collection {
    public ArrayList<Object> collection = new ArrayList<>(); //All objects have an arraylist variable "collection"

    //Constructor
    Collection() {
        //Add items to our ArrayList
        collection.add((String)"hello");
        collection.add((int)1);
        collection.add((float)3.7);
        collection.add((boolean)true);
        collection.add((String)"goodbye");
    }

    //returns a reference to ArrayList collection
    public ArrayList<Object> getCollection() {
        return collection;
    }       
}

//next() and hasNext() are the two core methods 
interface Iterator {
    public Object next();
    public boolean hasNext();
}

//generic iterator for a collection of Objects 
class CollectionIterator implements Iterator {
    Collection c = new Collection(); //new instance
    int currentItem = 0; //to keep track of position in collection

    //returns the current item if hasNext() is true, otherwise returns null; always use with hasNext()
    public Object next() {
        if(hasNext()) {
            Object result = c.getCollection().get(currentItem); //get current item
            currentItem++; //increment 
            return result; 
        }
        return null;
    }

    //checks if the position of currentItem is greater than the collection's size to determine hasNext()
    public boolean hasNext() {
        if (currentItem < c.getCollection().size()) {   //if current item is less than the size of the array
            return true;
        }
        return false;
    }
        
}
