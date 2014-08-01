mocktopus
=========

v 0.5
-----

This thing is approaching usable. You can look at it now

how to use

1. annotate your model objects
2. inject mocktopus service in place of real service
3. start your app and change stuff in the config screen
4. see results!

todo:
-----

* get a proper gitignore
* error handling in mocktopus
    * better exception handling
    * get other people to implement the library and see where they screw up
        * make sure these hot spots are noted in the readme
        * make sure these failures have good logging
* full logging support
    * interface parsing
    * object generation
* rotation support in ConfigurationActivity
* support lists of "Primitives" (integers, etc)
* expand on demo reddit api - get a feel for how to integrate with various apis and prove it's capable of doing so
    * story detail
    * user detail
* improve demo 
    * show what a "real" consumer activity would look like
    * move json viewer into another activity
* improve config activity
    * show currently selected value
* improve configuration
    * method options
        * error/success - how does this work in retrofit?
            * observables handle errors
            * callbacks have an onError callback
            * return value methods throw exceptions
    * observable options
        * delay
        * indefinite loading?
* add recursive child limits (if MyObject contains a MyObject, tries to add more forever. depth limit?)
* inheritance support
* private field support
* add callback support (for better retrofit support) - do people use this? kinda leaky iirc
* more annotations?
    * ignore (used to tell mocktopus to ignore certain fields that wont make sense. like if a model contains a Date or a LatLng for convenience reasons
    * string
    * int
* list modder options/settings?
* modder pattern for other objects?
* add some flavors to show how to use next to a "real" api
* label annotations
    * if, for example, your model fields arent "tester readable" you could give them alternate names for display in the config
    * maybe a description instead?

Proposals
---------
        
* add api passthrough (so some methods are mocked, some make real requests)

api passthrough should be an api level setting, it has its own "settings" that adds a new default, "passthrough"

instead of an object builder, you use an object modifier that iterates through 
all the fields in the response and modifies them to be the values set in the settings object, 
ignoring fields set to "passthrough." This would make working with recursive objects easier, 
as well as providing more realistic data.

* how should this deal with recursive objects?
    * brute force depth limit ("works" but not really intelligent)
    * modders for plain objects?
        * "Modders" modify an object after it has been inflated
        * allowing for more customized logic in object building
        * have an "inflation" pass, then a "modification" pass
            * how would this work with passthrough? just ignore them?

* mocktopus "contexts"
    * large apps may use 5+ distinct services, each activity may only use 1-2, provide activities a way to open just relevent services
        * maybe an annotation that explicitly lists the services used at the top of the activity?
        the config could then check what services to show.
            * with an intentservice, this may be abstracted away. doesnt mean it's not still the best way
    
* does it really have to be a singleton?
    * if it's not, there's probably going to be a lot of boilerplate code for a user to write. 
    more work for everyone to enable a purely hypothetical use case? sounds not worth it
    
* activation best practices?
    * action item
    * shake?
    * overlay button? like link bubble. can this be done to only show up while the app is open?
    
Pain points
----------
integrating mocktopus with real-world code can be painful. Whether due to weird model objects or app architectures that don't
follow best practices. This section is to make note of some of the troubles I've had when integrating it in "real world" projects. If
mocktopus isn't working for you, this section might help you figure out why.

* creating a service for each request. 
    * first off, don't do this. it's expensive. 
    * if you do, and you call initApi every time you'd use retrofit to build a service, you'll end up overwriting your settings
    so when you hit back and your activity is recreated, you'll end up with all defaults. This should definitely be logged
    and mocktopus should definitely warn you if you're overwriting an api/settings that has already been created
    * maybe this should throw an exception? might be a bit harsh. The library should 
    ideally support poor practice as much as possible
    
* make sure that any inner classes in your models are static. Otherwise mocktopus will end up trying to fill the parent.this reference
which is probably not what you want. From what ive seen, this usually ends up in an infinite loop during object creation. not fun.
    * there may be some way to fix this. there's already a check for "$this" but apparently that doesn't cover everything

