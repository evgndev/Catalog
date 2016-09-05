catalogApp.config(function ($translateProvider) {

    var englishTranslationTable = {
        headerTitle: 'Bike catalog',
        headerLabel: "",
        homePage: 'Home',
        itemsPage: 'Bikes',
        itemPage: 'view',
        itemTableHeader:'Bikes',
        itemCreationError: 'Item creation error',
        itemCreatesSuccess: 'Item was create',
        id: 'ID',
        name: 'Name',
        price: 'Price',
        description: 'Description',
        specs: 'Specs',
        paginationPrevious: 'Back',
        paginationNext: 'Next',
        createNew: 'Create item',
        openItem: 'Show',
        editItem: 'Edit',
        tableGetItemsError: 'Cannot get items from server',
        tableEmpty: 'No items',
        contacts: 'Info'
    };

    // add Russian translation
    $translateProvider.translations('en', englishTranslationTable);
});