package com.besuikerd.core.gui.event;

import com.besuikerd.core.gui.element.Element;
import com.besuikerd.core.utils.ReflectUtils;
import com.besuikerd.core.utils.ReflectUtils.Invokable;
import com.besuikerd.core.utils.collection.ArrayUtils;
import com.besuikerd.core.utils.functional.Predicate;

public class EventHandler implements IEventHandler{
	
	/**
	 * object handling incoming events
	 */
	protected Object handlerObject;
	
	public EventHandler(Object handlerObject) {
		this.handlerObject = handlerObject;
	}

	@Override
	public void post(final String name, Element e, Object... args) {
		args = ArrayUtils.prepend(e, args); //add e to the arguments
		Invokable i = ReflectUtils.getPartialMatchingInvokable(handlerObject, ReflectUtils.getAnnotatedMethods(handlerObject, EventHandle.class, new Predicate<EventHandle>() {
			@Override
			public boolean eval(EventHandle input) {
				return input.value() != null && input.value().equals(name);
			}
		}), args);
		if(i != null){
			i.invoke();
		} else{
			ReflectUtils.invokePartialMatchingMethod(handlerObject, name, args);
		}
	}

}
