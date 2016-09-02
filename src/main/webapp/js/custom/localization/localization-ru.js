catalogApp.config(function ($translateProvider) {

    var russianTranslationTable = {
        headerTitle: 'Каталог',
        headerLabel: "This is some catalog of something ...",
        homePage: 'Главная',
        itemsPage: 'Товары',
        itemPage: 'Товар',
        itemTableHeader:'It is items table',
        itemCreationError: 'Ошибка при создании',
        itemCreatesSuccess: 'Товар был добавлен',
        id: 'ИД',
        name: 'Имя',
        price: 'Цена',
        description: 'Описание',
        paginationPrevious: 'Назад',
        paginationNext: 'Далее',
        createNew: "Создать товар",
        tableGetItemsError: "Ошибка при получении каталога",
        tableEmpty: 'Нет товаров для отображения'
    };

    // add Russian translation
    $translateProvider.translations('ru', russianTranslationTable);
});