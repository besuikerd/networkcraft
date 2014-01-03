package nl.besuikerd.core.gui.element.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.besuikerd.core.gui.element.Element;

public abstract class BaseElementAdapter<E> implements IElementAdapter{
	protected List<E> elements;
	protected Set<InvalidationListener> listeners;

	public BaseElementAdapter(List<E> elements) {
		this.elements = elements;
		this.listeners = new HashSet<InvalidationListener>();
	}
	
	public BaseElementAdapter(){
		this(new ArrayList<E>());
	}
	
	@Override
	public int getElementCount() {
		return elements.size();
	}
	
	@Override
	public Element getElementAt(int index) {
		return createElementAt(elements.get(index), index);
	}
	
	public void append(E element){
		elements.add(element);
		notifyInvalidation();
	}
	
	public void remove(E element){
		if(elements.remove(element)){
			notifyInvalidation();
		}
	}
	
	public void clear(){
		elements.clear();
		notifyInvalidation();
	}
	
	private void notifyInvalidation(){
		for(InvalidationListener listener : listeners){
			listener.onInvalidation();
		}
	}
	
	@Override
	public void addInvalidationListener(InvalidationListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeInvalidationListener(InvalidationListener listener) {
		listeners.remove(listener);
	}
	
	public abstract Element createElementAt(E data, int index);
}
