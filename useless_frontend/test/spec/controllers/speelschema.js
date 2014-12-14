'use strict';

describe('Controller: SpeelschemaCtrl', function () {

  // load the controller's module
  beforeEach(module('mswFrontendApp'));

  var SpeelschemaCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    SpeelschemaCtrl = $controller('SpeelschemaCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
