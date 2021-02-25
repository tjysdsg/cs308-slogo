# API Lab Discussion
# Collections API Discussion

## Names and NETIDS
Oliver Rodas(oar5), Joshua Petitma (jmp157), Martha Aboagye(mfa23), Jiyang Tang (jt304)

### What is the purpose of each interface implemented by LinkedList?

Serializable: Capable of saving objects to a file and reloading it
Cloneable: Capable of being copied and creating a new object based on its content
Iterable: Makes it possible for objects  to be looped over
Deque: Elements can be removed and inserted at both ends. 
List: Elements can be added, accessed, and removed at any index, and can be iterated.
Queue: Elements are added in an order. Using remove with no parameters will remove the first item put in
Collection: It's capable of being inside static collection methods (sort, blah). It also supports typical adding/removing methods.

### What is the purpose of each superclass of HashMap?
Object: To be.
AbstractMap: Defines what a map is beyond the interface and the common methods
between all map implementations.


### How many different implementations are there for a Set?
9

### What methods are common to all collection(s)?
clear() 	
contains(Object o) 	
containsAll(Collection<?> c) 	
equals(Object o) 	
hashCode() 	
isEmpty() 	
iterator() 	
remove(Object o) 	
removeAll(Collection<?> c) 	
retainAll(Collection<?> c) 	
size() 	
toArray() 	
toArray(T[] a) 	
add(E e) 	
addAll(Collection<? extends E> c) 	

### What methods are common to all Queues?
remove
add
element
offer
peek
poll

### What is the purpose of the collection utility classes?

To store collection of like objects.