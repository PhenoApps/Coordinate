CREATE TABLE templates
    (
        _id 		INTEGER PRIMARY KEY AUTOINCREMENT,
        title		TEXT,
        type		INTEGER,
        rows		INTEGER,
        cols		INTEGER,

		erand       INTEGER CHECK(erand >= 0),
        ecells		TEXT,
        erows		TEXT,
        ecols		TEXT,

        cnumb		INTEGER,
        rnumb		INTEGER,

        options		TEXT,

        stamp		INTEGER
    )