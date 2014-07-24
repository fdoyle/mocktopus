mocktopus
=========

This thing is approaching usable. You can look at it now

how to use

1. annotate your model objects
2. inject mocktopus service in place of real service
3. start your app and change stuff in the config screen
4. see results!


todo:
-----

* expand on demo reddit api
    * story detail
    * user detail
* improve demo 
    * show what a "real" consumer activity would look like
    * move json viewer into another activity
* improve config activity
    * show currently selected value
* improve configuration
    * method options
        * error/success 
    * observable options
        * delay
        * indefinite loading?

* add primitives support (support int, not just Integer)
* add recursive child limits (if MyObject contains a MyObject, tries to add more forever. depth limit?)
* add callback support (for better retrofit support)
* more annotations?
    * string
    * int
* list builder options/settings?
* add some flavors to show how to use next to a "real" api
* support multiple retrofit apis at the same time

        
* add api passthrough (so some methods are mocked, some make real requests)
    * add "leave alone" as default option
    * instead of building out an object, your "builder" takes an object and modifies fields
    * would this be better done as a separate tool?
        * there's some conceptual overlap, but implementation and setup would probably be very different
        * would this require a third build type?

