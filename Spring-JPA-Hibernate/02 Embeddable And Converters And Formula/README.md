
If you don’t require polymorphic associations or queries, lean toward table-per-concrete class—in other words, if you never or rarely select bd from BillingDetails bd, and you have no class that has an association to BillingDetails. An explicit UNION-based mapping with InheritanceType.TABLE_PER_CLASS should be preferred because (optimized) polymorphic queries and associations will be possible later.

If you do require polymorphic associations (an association to a superclass, and hence to all classes in the hierarchy with a dynamic resolution of the concrete class at runtime) or queries, and subclasses declare relatively few properties (particularly if the main difference between subclasses is in their behavior), lean toward InheritanceType.SINGLE_TABLE. This approach can be chosen if it involves setting a minimal number of columns as nullable. You’ll need to convince yourself (and the DBA) that a denormalized schema won’t create problems in the long run.

If you do require polymorphic associations or queries, and subclasses declare many (non-optional) properties (subclasses differ mainly by the data they hold), lean toward InheritanceType.JOINED. Alternatively, depending on the width and depth of the inheritance hierarchy and the possible cost of joins versus unions, use InheritanceType.TABLE_PER_CLASS. This decision might require the evaluation of SQL execution plans with real data.



By default, choose InheritanceType.SINGLE_TABLE only for simple problems. For complex cases, or when a data modeler insists on the importance of NOT NULL constraints and normalization overrules you, you should consider the Inheritance-Type .JOINED strategy. At that point, you should ask yourself whether it may not be better to remodel inheritance as delegation in the class model. Complex inheritance is often best avoided for all sorts of reasons unrelated to persistence or ORM. Hibernate acts as a buffer between the domain and relational models, but that doesn’t mean you can ignore persistence concerns completely when designing the classes.



When you start thinking about mixing inheritance strategies, you must remember that implicit polymorphism in Hibernate is smart enough to handle exotic cases. Also, you must consider that you can’t put inheritance annotations on interfaces; this isn’t standardized in JPA.