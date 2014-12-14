'use strict';

describe('Controller: CompetitionCtrl', function () {

  // load the controller's module
  beforeEach(module('mswFrontendApp'));

  var CompetitionCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CompetitionCtrl = $controller('CompetitionCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
