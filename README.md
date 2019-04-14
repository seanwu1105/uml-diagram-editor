# UML Diagram Editor

A toy UML diagram editor.

## Introduction

This is an assignment for Object-Oriented Analysis and Design (SE6029*) in NCU, Taiwan. A complete implementation of the [specification](#Spec).

## Getting Start

Clone this repository.

``` bash
git clone https://github.com/seanwu1105/uml-diagram-editor.git
```

Use IntelliJ IDEA (recommended) or other Java IDE to open the project.

## Spec

The following is a prototype UI for the MUL editor.
![ui preview](https://i.imgur.com/syXfSsS.png)

This UML editor UI is divided into three regions. The first region allows users to place diagrams and make connections. This area is called "canvas area". On the left-hand side is a region called "taskbar" containing a lot of buttons for tool selections.

### Terminology

#### Basic Object

The boxes that represent a class or the ellipses that represent the use case are said to be "basic objects". Basic objects can be created by buttons and then moved around in the editing regions. As in common UML semantics, associations can be constructed between two basic objects.

#### Connection Line

A connection line is an entity between basic objects. We have three types of connection line. They are "association", "composition" and "generalization".

#### Composite Object

Just like many other object-oriented diagram editing tools. A tool user can select a set of objects and group them into a so-called "composite object". A composite object can be composed of a set of basic objects and this composite object can be composed with other basic objects or composite object to become a more complicated composite object. A composite object is a type of container which can be implemented by a tree hierarchy. Ungrouping a composite object is equal to tear down the top level of the tree.

#### The Depth of an Object

As in many other object-oriented diagram editing tool, every object has an attribute called "depth (from 0 to 99)". This attribute concerns the drawing order of the objects. A deeper object is drawn behind an object that has less depth. Besides, when a set of objects are overlapped. The object on top of the objects always intercepts the mouse and keyboard events. For example, when a mouse clicks on two overlapped objects, only the object on the top receives the mouse/keyboard events.

### UseCase A.1 Creating an UML Object

#### Precondition

This use case only applies to the modes of "class" and "use case".

1. Click the button and the button is changed to black color so that users know the current mode is controlled by this button.
2. Move the cursor to the editing area.
3. Click at `(x, y)` with the left mouse button, a basic object is created at `(x, y)` as its top-left-corner.
4. Users can repeat the steps 2. and 3. to create the same kind of objects until the mode is changed.

#### Alternatives 1.a

When other buttons in the taskbar are clicked, this mode is terminated and other mode controls the behaviors of the mouse.

### UseCase B.1 Creating an UML Connection Line

#### Precondition

This use case applies to the modes of "association line", "generalization line" and "composition line" in the taskbar.

1. In editing area, a user presses at `(x, y)` by mouse left button and `(x, y)` is inside a basic object A. The user keeps the mouse button pressed.
2. The user drags the mouse.
3. The user releases the mouse button at `(x', y')` and `(x', y')` is inside a basic object B.
4. A connection line is created between A and B. The arrow type of the connection line is displayed at the port of A.

#### Alternative 1.a

`(x, y)` is not inside any basic object. The following actions from mouse pressed and mouse drag to mouse released make no effects.

#### Alternative 3.a

`(x', y')` is not inside any basic objects. No connection line is created.

#### Definition

Please assume each basic object has 4 ports as in the following figure. A connection line is established between two ports of basic objects. A simple rule is used to compute which port should be chosen when the mouse is clicked. For example, when the user pressed the mouse in `(x, y)`, the distances to for ports can be computed. The port that has the shortest distance to `(x, y)` should be chosen as the connection port for the new connection line.

![ports preview](https://i.imgur.com/RzzWWgO.png)

### UseCase C.1 Select/Deselect a Single Object

#### Precondition

This use case only applies to the "select" mode in the taskbar.

#### Definition

We say an object is "selected" if its four connection ports are displayed as the figure shown above. On the other hand, when an object is not selected, the four connection ports should be hidden.

1. A user clicks at (x, y) which is inside an object X.
2. If any object in the editing area is "selected", deselect them.
3. Otherwise, make X as a "selected" object by showing its four connection ports.

#### Alternative 1.a

If `(x, y)` is not inside an object, do nothing. Also, deselect every object that is previously selected.

### UseCase C.2 Select/Deselect a Group of Objects

#### Precondition

This use case only applies to the "select" mode in the taskbar.

1. A user press mouse button at `(x1, y1)` without releasing the button and `(x1, y1)` is not inside any objects.
2. Deselect any object that is previously selected.
3. The user drags the mouse.
4. The user releases the mouse button at `(x2, y2)`.
5. Any object that is completely inside the rectangular area `(x1, y1, x2, y2)` are selected.

#### Alternative 4.a

If no object inside the rectangular area `(x1, y1, x2, y2)`, deselect all previously selected objects.

### UseCase D.1 Group Objects

#### Precondition

This use case only applies to the case that at least two objects are selected under "select" mode.

1. The user goes to Edit Menu and clicks "group".
2. The selected objects are grouped into a composite object.

### UseCase D.2 Ungroup Objects

#### Precondition

This use case only applies to the case that only one composite object has been selected.

1. The user goes to Edit Menu and clicks "ungroup".
2. The composite object is torn down one level.

### UseCase E.1 Move Object

#### Precondition

This use case only applies to "select" mode.

1. The user presses mouse button at `(x, y)` without releasing the button and `(x, y)` is inside an object X (including composite objects).
2. The user drags the mouse.
3. The user releases button at `(x', y')`.
4. Object X is moved to the new position `(x', y')`.
5. All the connection lines that connect to object X are redrawn.

> `(x, y)` could fall inside more than one objects (they are overlapped). In this case, the object on the top (with less depth) should be moved.

### UseCase F.1 Change Object Name

#### Precondition

This use case only applies to "select" mode when only one basic object is selected.

1. The user goes to Edit Menu and clicks "change object name".
2. A window pops up. The window has one text area, OK button and Cancel button.
3. The user enters string at text area and presses OK. The string entered should be displayed on the basic objects.

#### Alternative 3.a Cancel
User click Cancel button, the popped window disappears and do nothing.