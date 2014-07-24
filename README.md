mocktopus
=========

This thing isn't done yet, don't look at it!


todo:
-----

* expand on reddit api
    * story detail
    * user detail
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

